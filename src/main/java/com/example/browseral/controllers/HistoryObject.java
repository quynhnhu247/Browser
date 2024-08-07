package com.example.browseral.controllers;

import java.io.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class HistoryObject implements Serializable {

    String url;
    LocalDate date;
    Time time;

    @Override
    public String toString() {
        return "Url=" + url + " Date=" + date + " Time=" + time + '}';
    }


    public HistoryObject() {
    }

    public void addHist(String url, String fileName) {
        this.url = url;
        // this.fileName = fileName;
        this.date = LocalDate.now();
        this.time = Time.valueOf(LocalTime.now());

        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName,true);
            fw.write(url);
            fw.write(" ");
            fw.write(date.toString());
            fw.write(" ");
            fw.write(time.toString());
            fw.write(System.lineSeparator());
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            try {
                fw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    public void addHist(String url, LocalDate date, Time time, String fileName) {
        this.url = url;
        this.date = date;
        this.time = time;

        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName,true);
            fw.write(url);
            fw.write(" ");
            fw.write(date.toString());
            fw.write(" ");
            fw.write(time.toString());
            fw.write(System.lineSeparator());
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            try {
                fw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    public ArrayList<HistoryObject> getAllHistory(String fileName){
        ArrayList<HistoryObject> ar = new ArrayList();
        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(fileName));
            while(true){
                HistoryObject ho = new HistoryObject();
                StringTokenizer token = new StringTokenizer(br.readLine());
                while(token.hasMoreTokens()){
                    ho.url = token.nextToken();
                    ho.date = LocalDate.parse(token.nextToken(), DateTimeFormatter.ISO_DATE);
                    ho.time = Time.valueOf(token.nextToken());
                }

                if(ho == null){
                    break;
                }

                ar.add(ho);

            }
        } catch (Exception e) {
            System.out.println("End of File");
        }finally{
            try {
                br.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return ar;
    }

    public ArrayList<HistoryObject> getHistByDate(LocalDate StartDate,LocalDate EndDate,String fileName){
        ArrayList<HistoryObject> ar = new ArrayList();

        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(fileName));
            while(true){
                HistoryObject ho = new HistoryObject();
                StringTokenizer token = new StringTokenizer(br.readLine());
                while(token.hasMoreTokens()){
                    ho.url = token.nextToken();
                    ho.date = LocalDate.parse(token.nextToken(), DateTimeFormatter.ISO_DATE);
                    ho.time = Time.valueOf(token.nextToken());
                }

                if(ho.date.compareTo(StartDate)>=0 && ho.date.compareTo(EndDate)<=0)
                    ar.add(ho);

            }
        } catch (Exception e) {
            System.out.println("End of File");
        }finally{
            try {
                br.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return ar;

    }

    public void deleteHistByDate(LocalDate StartDate,LocalDate EndDate,String fileName){
        ArrayList<HistoryObject> ar= new ArrayList();

        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(fileName));
            while(true){
                HistoryObject ho = new HistoryObject();
                StringTokenizer token = new StringTokenizer(br.readLine());
                while(token.hasMoreTokens()){
                    ho.url = token.nextToken();
                    ho.date = LocalDate.parse(token.nextToken(), DateTimeFormatter.ISO_DATE);
                    ho.time = Time.valueOf(token.nextToken());
                }

                if(ho == null)
                {
                    break;
                }
                if(ho.date.compareTo(StartDate) <0 || ho.date.compareTo(EndDate)>0){
                    ar.add(ho);
                }

            }
        } catch (Exception e) {
            System.out.println("End of File");
        }finally{
            try {
                br.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        File f = new File(fileName);
        try {
            RandomAccessFile rf = new RandomAccessFile(f,"rw");
            rf.setLength(0);
            rf.close();

        } catch (Exception e) {
        }
        for (int i = 0; i < ar.size(); i++) {
            ar.get(i).addHist(ar.get(i).url, fileName);

        }

    }



}
