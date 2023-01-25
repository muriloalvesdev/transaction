package br.com.transaction.entrypoint.api;

import br.com.transaction.core.usecase.account.UseCaseAccount;
import br.com.transaction.entrypoint.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
class AccountsApi {

    private final UseCaseAccount useCaseAccount;

    @PostMapping
    public ResponseEntity<Void> save(
        @RequestBody @Validated final AccountDto dto) {
        final var accountId = this.useCaseAccount.save(dto);
        final var location = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/accounts/{id}")
            .buildAndExpand(accountId)
            .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{accountId}")
    public ResponseEntity<AccountDto> find(
        @PathVariable(name = "accountId") final String accountId) {
        final var dto = this.useCaseAccount.find(accountId);
        return ResponseEntity.ok(dto);
    }

}
