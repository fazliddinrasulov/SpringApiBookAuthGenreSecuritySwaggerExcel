package uz.pdp.back.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookExcelDto {
    private String id;
    private String title;
    private String author;
    private String genre;
}
