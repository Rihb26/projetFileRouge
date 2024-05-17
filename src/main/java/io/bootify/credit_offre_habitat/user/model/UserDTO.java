package io.bootify.credit_offre_habitat.user.model;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @Size(max = 255)
    private String nom;

    @Size(max = 255)
    private String prenom;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String motDePasse;

    private Integer numeroTelephone;

    @Size(max = 255)
    private String adresse;

    private List<@Size(max = 255) String> nouveauteBanque;

}
