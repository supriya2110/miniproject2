
package in.ashokit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="ALT_USER_DTLS")
@Data
public class UserDtlsEntity {
	
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
    private String email;
    private Long phno;
    private String accStatus;
    private String pwd;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<StudentEnqEntity> enquiries;

}
