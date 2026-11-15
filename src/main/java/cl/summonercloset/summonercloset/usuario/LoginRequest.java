package cl.summonercloset.summonercloset.usuario;

public record LoginRequest(String correo, String clave, Boolean comoAdmin) {
}