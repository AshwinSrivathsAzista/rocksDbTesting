package com.azista.rocksDb;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class MyApp {

    @Parameter(names = "--name", description = "User name", required = true)
    private String name;

    public String getName() {
        return name;
    }

    RocksDBRepositoryImpl db;

    public MyApp(String path) {

        db = new RocksDBRepositoryImpl(path);
        db.initialize();

    }

    // public static void main(String[] args) {

    // // MyApp jArgs = new MyApp();
    // // JCommander helloCmd = JCommander.newBuilder()
    // // .addObject(jArgs)
    // // .build();
    // // helloCmd.parse(args);
    // // System.out.println("Hello " + jArgs.getName());

    // System.out.println("Hello World!////////////////////////");

    // MyApp a = new MyApp("/rocks-db1");

    // // System.out.println(a.db.find("1"));
    // // System.out.println("........ phase 1");
    // // System.out.println(a.db.find("1"));
    // // System.out.println(a.db.find("15"));
    // // cf.thenAccept(result -> {
    // // System.out.println("Backup completed successfully");
    // // }).exceptionally(e -> {
    // // System.err.println("Backup failed: " + e.getMessage());
    // // return null;
    // // });
    // // try {
    // // cf.get(); // Block until the backup is complete
    // // System.out.println("Backup completed successfully");
    // // } catch (InterruptedException | ExecutionException e) {
    // // System.err.println("Backup failed: " + e.getMessage());
    // // }
    // // System.out.println("........ phase2");
    // // System.out.println(a.db.find("1"));
    // // System.out.println("........phase3");
    // // try {
    // // cf2.get(); // Block until the backup is complete
    // // System.out.println("Backup retrival completed successfully");
    // // System.out.println(a.db.find("1"));
    // // } catch (InterruptedException | ExecutionException e) {
    // // System.err.println("Backup retrival failed: " + e.getMessage());
    // // }
    // ////////////////////////
    // ////////////////////////
    // ////////////////////////
    // ////////////////////////

    // a.db.save("key-1", "null431431134gsg");

    // // int i = 0;
    // // while (i < 10000) {
    // // a.db.save("key-" + i, "value-" + i);
    // // i++;
    // // }

    // // boolean cf = a.db.doBackup("/MyBackup2");

    // // boolean cf3 = a.db.verifyBackup("/MyBackup2");

    // // a.db.delete("key-1");
    // // boolean cf2 = a.db.restoreBackup("/MyBackup2", "/rocks-db1");
    // // boolean cf2 = a.db.restoreBackup("/MyBackup2", "first-db");

    // System.out.println(a.db.find("key-1"));

    // // a.db.getAllKeys();

    // }

    // }

    public static void main(String[] args) {

        ColumnFamilies db = new ColumnFamilies();
        db.initialize();
 //todo continue from here

        // System.out.println(a.db.find("1"));
        // System.out.println("........ phase 1");
        // System.out.println(a.db.find("1"));
        // System.out.println(a.db.find("15"));
        // cf.thenAccept(result -> {
        // System.out.println("Backup completed successfully");
        // }).exceptionally(e -> {
        // System.err.println("Backup failed: " + e.getMessage());
        // return null;
        // });
        // try {
        // cf.get(); // Block until the backup is complete
        // System.out.println("Backup completed successfully");
        // } catch (InterruptedException | ExecutionException e) {
        // System.err.println("Backup failed: " + e.getMessage());
        // }
        // System.out.println("........ phase2");
        // System.out.println(a.db.find("1"));
        // System.out.println("........phase3");
        // try {
        // cf2.get(); // Block until the backup is complete
        // System.out.println("Backup retrival completed successfully");
        // System.out.println(a.db.find("1"));
        // } catch (InterruptedException | ExecutionException e) {
        // System.err.println("Backup retrival failed: " + e.getMessage());
        // }
        ////////////////////////
        ////////////////////////
        ////////////////////////
        ////////////////////////

        a.db.save("key-1", "null431431134gsg");

        // int i = 0;
        // while (i < 10000) {
        // a.db.save("key-" + i, "value-" + i);
        // i++;
        // }

        // boolean cf = a.db.doBackup("/MyBackup2");

        // boolean cf3 = a.db.verifyBackup("/MyBackup2");

        // a.db.delete("key-1");
        // boolean cf2 = a.db.restoreBackup("/MyBackup2", "/rocks-db1");
        // boolean cf2 = a.db.restoreBackup("/MyBackup2", "first-db");

        System.out.println(a.db.find("key-1"));

        // a.db.getAllKeys();

    }

}