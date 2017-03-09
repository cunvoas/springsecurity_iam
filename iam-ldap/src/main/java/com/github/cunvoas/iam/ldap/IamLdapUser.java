package com.github.cunvoas.iam.ldap;

import java.util.Date;
import java.util.List;

/**
 * User Object from LDAP.
 * @author CUNVOAS
 */
public class IamLdapUser {
    
    private String uid;

    private String initial;
    private String firstName;
    private String lastName;
    private String fullName;
    private String mail;
    private Date arrival;
    private Date departure;
    private List<String> rolesList;    // structure du type "APPLICATION, ROLE"
    
    public IamLdapUser() {
        super();
    }
    public IamLdapUser(String uid) {
        super();
        this.uid=uid;
    }
    
    
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uid == null) ? 0 : uid.hashCode());
        return result;
    }
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof IamLdapUser)) {
            return false;
        }
        IamLdapUser other = (IamLdapUser) obj;
        if (uid == null) {
            if (other.uid != null) {
                return false;
            }
        } else if (!uid.equals(other.uid)) {
            return false;
        }
        return true;
    }

    /**
     * Getter for uid.
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Setter for uid.
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Getter for initial.
     * @return the initial
     */
    public String getInitial() {
        return initial;
    }

    /**
     * Setter for initial.
     * @param initial the initial to set
     */
    public void setInitial(String initial) {
        this.initial = initial;
    }

    /**
     * Getter for firstName.
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for firstName.
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for lastName.
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for lastName.
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for fullName.
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Setter for fullName.
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Getter for mail.
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Setter for mail.
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Getter for arrival.
     * @return the arrival
     */
    public Date getArrival() {
        return arrival;
    }

    /**
     * Setter for arrival.
     * @param arrival the arrival to set
     */
    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    /**
     * Getter for departure.
     * @return the departure
     */
    public Date getDeparture() {
        return departure;
    }

    /**
     * Setter for departure.
     * @param departure the departure to set
     */
    public void setDeparture(Date departure) {
        this.departure = departure;
    }
    /**
     * Getter for rolesList.
     * @return the rolesList
     */
    public List<String> getRolesList() {
        return rolesList;
    }
    /**
     * Setter for rolesList.
     * @param rolesList the rolesList to set
     */
    public void setRolesList(List<String> rolesList) {
        this.rolesList = rolesList;
    }
    
    
    /**
     * Check the entry/exit Dates.
     * @return
     */
    public boolean isActive() {
        boolean active=false;
        if (arrival!=null) {
            Date nowDate = new Date();
            if (departure==null) {
                active = nowDate.after(arrival);
            } else {
                active = nowDate.after(arrival) && nowDate.before(departure);
            }
        }
        return active;
    }

}
