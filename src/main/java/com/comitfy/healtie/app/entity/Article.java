package com.comitfy.healtie.app.entity;

import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.dbUtil.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
@AttributeOverride(name = "uuid", column = @Column(name = "article_uuid"))
public class Article extends BaseEntity {


    @Column
    private String title;


    @Column(columnDefinition = "Text", length = 100000)
    private String description;

    @Column
    private LocalDate publishedDate;

    @ManyToMany
    private Set<Tag> tags;

    @Column
    @Enumerated(EnumType.STRING)
    private LanguageEnum languageEnum;

    @ManyToMany
    private Set<Category> categoryList;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn()
    private User user;

    @ManyToMany
    private Set<User> userLikes;


    @ManyToMany
    private Set<User> userSaves;


    public void removeLike(User user) {
        this.userLikes.remove(user);
    }


    public void addLike(User user) {
        this.userLikes.add(user);
    }


    public void removeSave(User user) {
        this.userSaves.remove(user);
    }

    public void addSave(User user) {
        this.userSaves.add(user);
    }


}
