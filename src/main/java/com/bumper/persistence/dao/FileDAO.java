/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bumper.persistence.dao;

import com.bumper.utils.pojo.File;

/**
 *
 * @author math
 */
public class FileDAO extends AbstractGenericDAO<File> {

    public FileDAO() {
        super(File.class);
    }

}
