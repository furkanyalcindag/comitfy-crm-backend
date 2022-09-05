package com.comitfy.healtie.app.entity;

import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "doctor_uuid"
        )
)
public class Doctor extends BaseEntity {

    @Column
    private String title;

    @Column(unique = true)
    private String diplomaNo;

    @Column
    @Enumerated(EnumType.STRING)
    private LanguageEnum languageEnum;

    @Column
    private String address;


    @Column(columnDefinition = "Text",length = 100000)
    private String about;

    @Column
    private String branch;

    @Column
    private String phone;

    @Column
    private String clinicName;

    @Column
    private  String profileImage;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AcademicInfo> academicInfoList;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Certificate> certificateList;

/*    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Article> articleList;*/

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Experience> experienceList;



}



