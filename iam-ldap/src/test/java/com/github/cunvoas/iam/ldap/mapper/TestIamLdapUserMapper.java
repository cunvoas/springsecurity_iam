package com.github.cunvoas.iam.ldap.mapper;

import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.cunvoas.iam.ldap.mapper.IamLdapUserMapper;

public class TestIamLdapUserMapper {

    private Calendar calendar;
    private String dateFormated;

    @Before
    public void setUp() throws Exception {
        calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.FEBRUARY, 28, 21, 22, 23);
        calendar.set(Calendar.MILLISECOND, 789);
        
        dateFormated="20140228212223.000000Z";
        
    }

    @Test
    public void test01GetSdf() {
        
        DateFormat sdf = IamLdapUserMapper.getSdf();
        Assert.assertEquals("format", dateFormated, sdf.format(calendar.getTime()));
        
        try {
            calendar.set(Calendar.MILLISECOND, 0);
            
            Date parsed = sdf.parse(dateFormated);
            Assert.assertEquals("parse", calendar.getTime(), parsed);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
    }

}
