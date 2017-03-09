package com.github.cunvoas.iam.security.helper;

import java.util.Comparator;

import com.github.cunvoas.iam.security.authority.IamGrantedAuthority;

class IamGrantedAuthorityComparator implements Comparator<IamGrantedAuthority> {

    public static final IamGrantedAuthorityComparator INSTANCE = new IamGrantedAuthorityComparator();

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(IamGrantedAuthority o1, IamGrantedAuthority o2) {
        return o1.getAuthority().compareTo(o2.getAuthority());
    }

}