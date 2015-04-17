/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bumper.persistence.dao;

import com.bumper.utils.pojo.Dataset;
import com.bumper.utils.pojo.Issue;
import com.bumper.utils.pojo.IssueType;
import com.bumper.utils.pojo.Project;
import com.bumper.utils.pojo.Resolution;
import com.bumper.utils.pojo.Severity;
import com.bumper.utils.pojo.Status;
import java.util.List;

/**
 *
 * @author math
 */
public class IssueDAO extends AbstractGenericDAO<Issue> {

    public IssueDAO() {
        super(Issue.class);
    }

    public Issue findByExternalId(String externalID) {
        return this.select().where("exteralId", externalID).findOne();
    }

    public List<Issue> findByDataset(Dataset dataset) {
        return this.select().where("dataset", dataset).findAll();
    }

    public List<Issue> findByProject(Project project) {
        return this.select().where("project", project).findAll();
    }

    public List<Issue> findByStatus(Status status) {
        return this.select().where("status", status).findAll();
    }

    public List<Issue> findByResolution(Resolution resolution) {
        return this.select().where("resolution", resolution).findAll();
    }

    public List<Issue> findBySeverity(Severity severity) {
        return this.select().where("severity", severity).findAll();
    }

    public List<Issue> findByIssueType(IssueType type) {
        return this.select().where("issueType", type).findAll();
    }

}