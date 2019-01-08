package acc.model;

import java.io.Serializable;

public class Address implements Serializable{

    public Address(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address;
    }
    private String address;

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }
}
