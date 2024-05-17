package io.bootify.credit_offre_habitat.list_favoris.model;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ListFavorisDTO {

    private Long id;
    private List<@Size(max = 255) String> listFavoris;
    private Long user;

}
