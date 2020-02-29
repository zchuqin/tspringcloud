package stoner.tspringcloud.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Principal {
    private String name;

    @Override
    public String getName() {
        return name;
    }
}
