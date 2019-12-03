package redisCache.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -8720470490474017235L;
    private String id;
    private String name;
    private String address;
    private String tel;
    private int age;

    @Override
    public String toString() {
        return "id= " + this.id + " name= " + this.name;
    }
}
