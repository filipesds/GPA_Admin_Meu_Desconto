package utils;

public class Users {
	private String usuarioDev;
	private String usuarioHlg;
	private String senhaDev;
	private String senhaHlg;

	public Users(){
		ProjectProperties prop = new ProjectProperties();
		String usuario = prop.getSeleniumProperties("usuario");
		if(usuario.equals("dev")){
			this.usuarioDev = "admin";
			this.senhaDev = "gpa@focado";
		}
		if (usuario.equals("hlg")){
			this.usuarioHlg = "admin";
			this.senhaHlg = "gpa@focado";
		}
	}

	public String getUsuarioDev(){
		return usuarioDev;
	}

	public String getUsuarioHlg(){
		return usuarioHlg;
	}

	public String getSenhaDev(){
		return senhaDev;
	}

	public String getSenhaHlg(){
		return senhaHlg;
	}
}