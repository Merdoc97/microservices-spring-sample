package hello.dto;

public class ResponseDTO {

  private String response;

  public ResponseDTO() {
  }

  public ResponseDTO(String response) {
    this.response = response;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }
}
