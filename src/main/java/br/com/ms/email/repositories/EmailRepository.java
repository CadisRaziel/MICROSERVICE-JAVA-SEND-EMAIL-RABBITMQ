package br.com.ms.email.repositories;

import br.com.ms.email.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//JpaRepository -> sempre tipo com o Model e o ID
public interface EmailRepository extends JpaRepository<EmailModel, UUID> {
}
