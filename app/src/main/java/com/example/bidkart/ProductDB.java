package com.example.bidkart;

import java.util.ArrayList;

public class ProductDB {
    private ArrayList<Product> pdb;

    public ProductDB(ArrayList<Product> pdb) {
        this.pdb = pdb;
    }
    public ProductDB(){
        pdb=new ArrayList<Product>();
    }

    public ArrayList<Product> getPdb() {
        return pdb;
    }

    public void setPdb(ArrayList<Product> pdb) {
        this.pdb = pdb;
    }
    public void add(Product p){
        pdb.add(p);
    }
    public void remove(Product p){
        pdb.remove(p);
    }
    public void set(Product p,int pos){
        pdb.set(pos,p);
    }
    public int searchByID(Product p){
        int pos=-1;
        for (int i=0;i<pdb.size();i++){
            if(p.getId()==pdb.get(i).getId()){
                pos=i;
                break;
            }
        }
        return pos;
    }
    public  void clear(){
        pdb.clear();
    }
}
