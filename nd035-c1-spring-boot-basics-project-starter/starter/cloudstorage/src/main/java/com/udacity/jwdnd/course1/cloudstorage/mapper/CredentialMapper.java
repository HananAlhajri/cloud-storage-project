package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, password, key, userId) VALUES (#{url}, #{username}, #{password}, #{key}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int create(Credentials credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userId =#{userId}")
    List<Credentials> findCredentialsByUserId(Integer userId);

    @Update("UPDATE CREDENTIALS SET url =#{credential.url}, username =#{credential.username}, password =#{credential.password}, key =#{credential.key} WHERE credentialId =#{credentialId}")
    void update(Credentials credential, Integer credentialId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void deleteById(Integer credentialId);
}