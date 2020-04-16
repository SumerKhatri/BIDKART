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
    public Product searchByID(String id){
        int pos=-1;
        for (int i=0;i<pdb.size();i++){
            if(id.equals(pdb.get(i).getId())){
                pos=i;
                break;
            }
        }
        if(pos!=-1)
        return pdb.get(pos);
        else
            return null;
    }

    public Product getProduct(int i){
        return pdb.get(i);
    }

    public ArrayList<Product> searchByTitle(String title){
        int pos=-1;

        ArrayList<Product>items=new ArrayList<>();
        for (int i=0;i<pdb.size();i++){
            if(pdb.get(i).getTitle().contains(title)){
                pos=i;
                items.add(pdb.get(i));
            }
        }

        if(pos!=-1)
            return items;
        else
            return null;
    }
    public  void clear(){
        pdb.clear();
    }
}
