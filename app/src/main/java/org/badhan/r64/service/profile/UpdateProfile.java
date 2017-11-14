package org.badhan.r64.service.profile;

import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.core.ServiceResponse;
import org.badhan.r64.entity.Cadre;

public final class UpdateProfile {

    private UpdateProfile(){}


    public static class Request extends ServiceRequest{
        public String displayName;
        public String email;
        public String cadreId;
        public String batch;
        public String homeDistrict;
        public String postingAddress;
        public String bloodGroup;
        public String university;
        public String session;

        public Request(){}

        public Request(String displayName, String email) {
            this.displayName = displayName;
            this.email = email;
        }

        public String getHomeDistrict() {
            return homeDistrict;
        }

        public void setHomeDistrict(String homeDistrict) {
            this.homeDistrict = homeDistrict;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCadreId() {
            return cadreId;
        }

        public void setCadreId(String cadreId) {
            this.cadreId = cadreId;
        }

        public String getBatch() {
            return batch;
        }

        public void setBatch(String batch) {
            this.batch = batch;
        }

        public String getPostingAddress() {
            return postingAddress;
        }

        public void setPostingAddress(String postingAddress) {
            this.postingAddress = postingAddress;
        }

        public String getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public String getUniversity() {
            return university;
        }

        public void setUniversity(String university) {
            this.university = university;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }
    }

    public static class Response extends ServiceResponse {
        public Cadre cadre;
    }
}
