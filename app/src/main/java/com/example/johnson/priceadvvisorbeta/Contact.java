package com.example.johnson.priceadvvisorbeta;

/**
 * Created by johnson on 01-May-16.
 */

/**
 * Created by johnson on 29-Mar-16.
 */
public class Contact {
    Integer id;
    String name, phone, password, email;

    //name functions
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Integer id() {
        return this.id;
    }

    //phone number functions
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    //email number functions
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    //password number functions
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    //contact for image
    // private variables
    int _id;
    String _name;
    byte[] _image;


    // Empty constructor

    public Contact() {


    }

    // constructor
    public Contact(int keyId, String name, byte[] image) {
        this._id = keyId;
        this._name = name;
        this._image = image;

    }
    // constructor


    public Contact(int keyId) {
        this._id = keyId;

    }

    // getting ID
    public int getID() {
        return this._id;
    }


    // setting id
    public void setID(int keyId) {
        this._id = keyId;
    }

    // getting name
    public String getNameImage() {
        return this._name;
    }

    // setting name
    public void setNameImage(String name) {
        this._name = name;
    }

    // getting phone number
    public byte[] getImage() {
        return this._image;
    }

    // setting phone number
    public void setImage(byte[] image) {
        this._image = image;
    }

    //users details
    int idu;

    // getting ID
    public int getIDu() {
        return this.idu;
    }


    // setting id
    public void setIDu(int keyId) {
        this.idu = id;
    }


}
