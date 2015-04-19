/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bumper.persistence.dao;

import com.bumper.utils.pojo.Dataset;

/**
 *
 * @author math
 */
public class DatasetDAO extends AbstractGenericDAO<Dataset> {

    /**
     *
     */
    public DatasetDAO() {
        super(Dataset.class);
    }

    /**
     *
     * @param name
     * @return
     */
    public Dataset findByName(String name) {
        return this.select().where("name", name).findOne();
    }

}
