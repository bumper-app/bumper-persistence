/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bumper.persistence.dao;

import com.bumper.utils.pojo.People;

/**
 *
 * @author math
 */
public class PeopleDAO extends AbstractGenericDAO<People> {

    public PeopleDAO() {
        super(People.class);
    }

}
