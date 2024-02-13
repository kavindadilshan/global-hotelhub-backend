package com.bolton.globalhotelhub.entity;

import com.bolton.globalhotelhub.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String contactNumber;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredDateTime;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<SearchHistory> searchHistories= new ArrayList<>();


}
