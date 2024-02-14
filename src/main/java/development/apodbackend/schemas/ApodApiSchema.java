package development.apodbackend.schemas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ApodApiSchema {
    private String title;
    private String picture;
    private String explanation;
    private String date;
}
