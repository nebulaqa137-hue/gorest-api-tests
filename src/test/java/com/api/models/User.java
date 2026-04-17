package com.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User — modelo que representa el objeto User de GoRest API.
 *
 * @JsonInclude(NON_NULL) → no envía campos null en el body del request.
 * @JsonIgnoreProperties  → tolera campos extra en responses futuras.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private Integer id;
    private String name;
    private String email;
    private String gender;  // "male" | "female"
    private String status;  // "active" | "inactive"
}
