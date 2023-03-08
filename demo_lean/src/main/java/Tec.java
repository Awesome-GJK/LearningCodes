import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tec
 *
 * @author: gaojiankang
 * @date: 2022/10/13 16:26
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tec {

    private String id;
    private String name;
    private String age;
    private List<Stu> stus;
}
