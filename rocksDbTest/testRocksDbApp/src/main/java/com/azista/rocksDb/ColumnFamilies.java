package com.azista.rocksDb;

import java.io.*;
import java.nio.file.*;
import org.rocksdb.*;

public class ColumnFamilies {

    // private final static String NAME = "first-db";
    File dbDir;
    RocksDB db;
    private final static String dbPath = "/rocks-db1/second-db";

    // public ColumnFamilies(String path) {
    //     this.dbPath = path;
    // }

    void initialize() {
        RocksDB.loadLibrary();
        final Options options = new Options();
        options.setCreateIfMissing(true);
        // dbDir = new File(dbPath, NAME); ////////////////
        try {

            // System.out.println(dbDir.toPath());
            // // System.out.println(dbDir.getAbsoluteFile().toPath());

            // Files.createDirectories(dbDir.getParentFile().toPath());
            // Files.createDirectories(dbDir.getAbsoluteFile().toPath());

            // System.out.println(dbDir.getAbsolutePath());

            // db = RocksDB.open(options, dbDir.getAbsolutePath());
            db = RocksDB.open(options, dbPath);

        } catch (Exception ex) {
            System.out.println(
                    "Error initializng RocksDB, check configurations and permissions, exception: {}, message: {}, stackTrace: {}");
        }
        System.out.println("RocksDB initialized and ready to use");
    }

    public ColumnFamilyHandle createColumnfamily(String key) throws RocksDBException {
        // create column family
        final ColumnFamilyHandle columnFamilyHandle = db
                .createColumnFamily(new ColumnFamilyDescriptor(key.getBytes(), new ColumnFamilyOptions()));
        return columnFamilyHandle;
    }

    public synchronized void save(String key, String value) {
        System.out.println("save " + key + " " + value);
        try {
            db.put(key.getBytes(), value.getBytes());
        } catch (RocksDBException e) {
            System.out.println("Error saving entry in RocksDB, cause: {}, message: {}");
        }
    }

    public String find(String key) {
        // System.out.println("find");
        String result = null;
        try {
            byte[] bytes = db.get(key.getBytes());
            if (bytes == null)
                return null;
            result = new String(bytes);
        } catch (RocksDBException e) {
            System.out.println("Error retrieving the entry in RocksDB from key: {}, cause: {}, message: {}");
        }
        return result;
    }

    public void delete(String key) {
        System.out.println("delete " + key);
        try {
            db.delete(key.getBytes());
        } catch (RocksDBException e) {
            System.out.println("Error deleting entry in RocksDB, cause: {}, message: {}");
        }
    }

    public void getAllKeys() {
        RocksIterator itr = db.newIterator(); // Get the iterator object
        /*
         * Seek to first will start iterating from the first record of Db.
         * Similarly you can use seekToLast() to iterate from last record or iterate in
         * reverse direction.
         */
        itr.seekToFirst();
        // log.info("Iterating all keys..");
        while (itr.isValid()) {
            String key = new String(itr.key());
            String value = new String(itr.value());
            System.out.println("Iterating at key- a " + key + " and value - " + value);
            itr.next();
        }
        itr.close();
    }

    /**
     * // * Performs a backup of the database to the given directory
     * // *
     * // * @param relativePath
     * // * @param backupDir
     * // * @return a future that can be used to know when the backup has finished
     * and if there was any error
     * //
     */

    public static void verifyBackupDirectory(String backupDir, boolean mustExist) throws IOException {
        Path path = FileSystems.getDefault().getPath(backupDir);
        if (path.toFile().exists()) {
            if (!path.toFile().isDirectory()) {
                throw new FileSystemException(backupDir, null,
                        "File '" + backupDir + "' exists and is not a directory");
            }

            boolean isEmpty = true;
            boolean isBackupDir = false;
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
                for (Path p : dirStream) {
                    isEmpty = false;
                    if (p.endsWith("meta")) {
                        isBackupDir = true;
                        break;
                    }
                }
            }

            if (!isEmpty && !isBackupDir) {
                throw new FileSystemException(backupDir, null,
                        "Directory '" + backupDir + "' is not a backup directory");
            }
            if (!Files.isWritable(path)) {
                throw new FileSystemException(backupDir, null, "Directory '" + backupDir + "' is not writable");
            }
        } else {
            if (mustExist) {
                throw new FileSystemException(backupDir, null, "Directory '" + backupDir + "' does not exist");
            } else {
                Files.createDirectories(path);
            }
        }
    }

    public boolean doBackup(String backupDir) {

        // ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        // CompletableFuture<Void> cf = new CompletableFuture<>();
        // executor.execute(() -> {
        // YRDB db = null;
        try {
            verifyBackupDirectory(backupDir, false);
        } catch (IOException e) {
            // log.warn("Invalid backup directory: {} ", e.toString());
            // cf.completeExceptionally(e);
            System.out.println(e.toString());
            return false;
        }
        try (BackupEngineOptions opt = new BackupEngineOptions(backupDir);
                BackupEngine backupEngine = BackupEngine.open(Env.getDefault(), opt);) {
            // db = getRdb(relativePath, false);
            backupEngine.createNewBackup(db, true);
            // cf.complete(null);

            System.out.println(backupEngine.getBackupInfo());

        } catch (Exception e) {
            // log.warn("Got error when creating the backup: {} ", e.getMessage());
            // cf.completeExceptionally(e);
            System.out.println(e.toString());
            return false;
        }
        // finally {
        // if (db != null) {
        // dispose(db);
        // }
        // }
        // });

        return true;

    }

    public boolean restoreBackup(String backupDir, String restoreTo) {
        // ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        // CompletableFuture<Void> cf = new CompletableFuture<>();
        // executor.execute(() -> {
        try {
            BackupEngineOptions opt = new BackupEngineOptions(backupDir);
            BackupEngine backupEngine = BackupEngine.open(Env.getDefault(), opt);
            RestoreOptions restoreOpt = new RestoreOptions(false);

            String absolutePath = getAbsolutePath(restoreTo);
            backupEngine.restoreDbFromLatestBackup(absolutePath, absolutePath, restoreOpt);

            // cf.complete(null);
        } catch (Exception e) {
            // cf.completeExceptionally(e);
            System.out.println(e.toString());

            return false;
        }
        // });

        return true;
    }

    private String getAbsolutePath(String relativePath) {
        return dbPath + "/" + relativePath;
    }

    //////////////////////
    /////////////////////
    //////////////////////
    /////////////////////
    //////////////////////
    /////////////////////
    //////////////////////
    /////////////////////

}
