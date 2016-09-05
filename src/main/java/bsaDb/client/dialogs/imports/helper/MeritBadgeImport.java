package bsaDb.client.dialogs.imports.helper;

import objects.databaseObjects.Counselor;
import objects.databaseObjects.Requirement;

import java.util.List;
import java.util.Set;

/**
 * Created by Nathanael on 2/28/2016
 */
public class MeritBadgeImport {

    private List<Counselor> counselorList;
    private Set<Requirement> requirementSet;

    public List<Counselor> getCounselorList() {
        return counselorList;
    }

    public void setCounselorList(List<Counselor> counselorList) {
        this.counselorList = counselorList;
    }

    public Set<Requirement> getRequirementSet() {
        return requirementSet;
    }

    public void setRequirementSet(Set<Requirement> requirementSet) {
        this.requirementSet = requirementSet;
    }
}
