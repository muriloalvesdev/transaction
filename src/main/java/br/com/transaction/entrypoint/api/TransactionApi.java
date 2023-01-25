package br.com.transaction.entrypoint.api;

import br.com.transaction.core.usecase.transaction.UseCaseTransaction;
import br.com.transaction.entrypoint.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
class TransactionApi {

    private final UseCaseTransaction usecase;

    @PostMapping()
    public ResponseEntity<Void> save(@RequestBody @Validated final TransactionDto dto) {
        final var transactionId = this.usecase.save(dto);
        final var location = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/transactions/{id}")
            .buildAndExpand(transactionId)
            .toUri();
        return ResponseEntity.created(location).build();
    }

}
