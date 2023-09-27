package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CredentialService {

    private final CredentialMapper credentialMapper;

    public int createCredential(Credentials credential){
        return credentialMapper.create(credential);
    }

    public void updateCredential(Credentials credential, Integer credentialId){
        credentialMapper.update(credential, credentialId);
    }

    public List<Credentials> getAllCredentials(Integer userId) {
        return credentialMapper.findCredentialsByUserId(userId);
    }

    public void deleteCredential(Integer credentialId) {
            credentialMapper.deleteById(credentialId);
    }

}
