package rs.raf.userservice.mapper;

import org.springframework.stereotype.Component;
import rs.raf.userservice.domain.Client;
import rs.raf.userservice.dto.ClientCreateDto;
import rs.raf.userservice.dto.ClientDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class ClientMapper {

    public ClientDto clientToClientDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setEmail(client.getEmail());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setUsername(client.getUsername());
        clientDto.setDateOfBirth(client.getDateOfBirth());
        clientDto.setMembershipCardNumber(client.getMembershipCardNumber());
        clientDto.setScheduledTrainings(client.getScheduledTrainings());
        return clientDto;
    }

    public Client clientCreateDtoToClient(ClientCreateDto clientCreateDto) {
        Client client = new Client();
        client.setEmail(clientCreateDto.getEmail());
        client.setFirstName(clientCreateDto.getFirstName());
        client.setLastName(clientCreateDto.getLastName());
        client.setUsername(clientCreateDto.getUsername());
        client.setPassword(clientCreateDto.getPassword());
        client.setDateOfBirth(clientCreateDto.getDateOfBirth());
        client.setMembershipCardNumber(generateMembershipCard(clientCreateDto));
        client.setScheduledTrainings(0);
        client.setRole("client");
        return client;
    }

    private String generateMembershipCard(ClientCreateDto clientCreateDto) {
        String initials = clientCreateDto.getFirstName().substring(0, 1).toUpperCase() +
                clientCreateDto.getLastName().substring(0, 1).toUpperCase();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        String birthDate = clientCreateDto.getDateOfBirth().format(formatter);

        Random random = new Random();
        int randomNumber = random.nextInt(90 - 10) + 10; // Generisanje random dvocifrenog broja

        return initials + birthDate + randomNumber;
    }
}
