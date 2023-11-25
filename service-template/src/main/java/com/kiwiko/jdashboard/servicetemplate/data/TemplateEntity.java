package com.kiwiko.jdashboard.servicetemplate.data;

import com.kiwiko.jdashboard.framework.data.LongDataEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import javax.annotation.Nonnull;

@Entity
@Table(name = "TEST_templates")
public class TemplateEntity implements LongDataEntity {
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Nonnull
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
