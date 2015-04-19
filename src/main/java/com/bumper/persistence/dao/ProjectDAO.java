/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bumper.persistence.dao;

import com.bumper.utils.pojo.Project;
import java.util.List;

/**
 *
 * @author math
 */
public class ProjectDAO extends AbstractGenericDAO<Project> {

    /**
     *
     */
    public ProjectDAO() {
        super(Project.class);
    }

    /**
     *
     * @param name
     * @return
     */
    public Project findByName(String name) {
        return this.select().where("name", name).findOne();
    }

    /**
     *
     * @param dataset
     * @return
     */
    public List<Project> findByDataset(String dataset) {
        return this.select().where("dataset", dataset).findAll();
    }

}
