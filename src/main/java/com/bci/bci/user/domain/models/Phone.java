package com.bci.bci.user.domain.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)

@Entity
@Table(name = "phones")
public class Phone {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Version
    @Column(name = "version", columnDefinition = "BIGINT DEFAULT 1")
    private Long version;

    @Column(name = "number")
    private Long number;

    @Column(name = "city_code")
    private Integer cityCode;

    @Column(name = "country_code")
    private String countryCode;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;

        Phone phone = (Phone) o;
        return Objects.equals(id, phone.id) && Objects.equals(user, phone.user) && Objects.equals(version, phone.version) && Objects.equals(number, phone.number) && Objects.equals(cityCode, phone.cityCode) && Objects.equals(countryCode, phone.countryCode);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(user);
        result = 31 * result + Objects.hashCode(version);
        result = 31 * result + Objects.hashCode(number);
        result = 31 * result + Objects.hashCode(cityCode);
        result = 31 * result + Objects.hashCode(countryCode);
        return result;
    }
}
