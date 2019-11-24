## WS-Bank

WS Bank berbasiskan JAX WS dengan layanan:
1.  validate, dengan parameter nomor rekening
2.  generateVA, dengan parameter nomor rekening
3.  getDataNasabah, dengan parameter nomor rekening
4.  transfer, dengan parameter norek sender, receiver, dan amount
5.  checkTransaction, dengan parameter norek, amount, start date, dan end date

Basis Data
account(nama, no_rekening, balance)
transaction(id, id_account, type, amount, account_number, time)
virtual_account(no_rekening, no_virtual_account)

# Link Deployment : [ws-bank](http://13.229.224.101:8080/engima/WSBank)

## Pembagian tugas dppl
1. CI/CD : 13517081
2. Ekslporasi dan setup mesin deployment : 13517081