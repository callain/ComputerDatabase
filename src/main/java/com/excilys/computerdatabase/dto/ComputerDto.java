package com.excilys.computerdatabase.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.excilys.computerdatabase.annotation.Date;

public class ComputerDto {
	private String id;
	
	@NotEmpty(message = "{NotEmpty.computerDto.name}") @Size(min = 2, max = 255, message = "{Size.computerDto.name}")
	private String name;
	
//	@Pattern(regexp = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])", message = "{date.error}" )
	@Date
	private String introduced;
	
//	@Pattern(regexp = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])", message = "{date.error}" )
	@Date
	private String discontinued;
	
	private String companyId;
	
	public static class Builder {
        ComputerDto computer;

        private Builder() {
            computer = new ComputerDto();
        }

        public Builder id(String id) {
            if(id != null)
                this.computer.id = id;
            return this;
        }

        public Builder name(String name) {
            this.computer.name = name;
            return this;
        }

        public Builder introduced(String introduced) {
           this.computer.introduced = introduced;
            return this;
        }

        public Builder discontinued(String discontinued) {
            this.computer.discontinued = discontinued;
            return this;
        }

        public Builder companyId(String companyId) {
            this.computer.companyId = companyId;
            return this;
        }

        public ComputerDto build() {
            return this.computer;
        }
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIntroduced() {
		return introduced;
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
