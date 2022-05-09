package com.hospital.restapi;

import java.util.Set;

import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import com.hospital.restapi.dao.SystemUserPersistence;
import com.hospital.restapi.model.SystemUser;
import com.hospital.restapi.model.UserGroup;

public class LocalIdentityStore implements IdentityStore {

    @Inject
    private SystemUserPersistence daoUser;

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        try {
            SystemUser user = daoUser.findByUsername(credential.getCaller());
            if (user.getPassword().equals(credential.getPasswordAsString())) {
                UserGroup userGroup = user.getUserGroup();
                Set<String> roles = Set.of(userGroup != null ? userGroup.getName() : "");

                return new CredentialValidationResult(user.getUsername(), roles);
            }
        } catch (Exception e) {
        }
        return CredentialValidationResult.INVALID_RESULT;
    }
}
