## 동시성 제어 Tutorial

### synchronized 키워드
- 분산 환경에서 사용할 수 없음

### MySQL에서 JPA의 Lock을 이용
- JPA의 Lock에는 Optimistic(낙관적) Lock, Pessimistic(비관적) Lock이 있다.

#### Pessimistic Lock
- Pessimistic Lock은 비관적이다. 경합이 발생할 것을 일단 가정한다.
- 그래서 조회할 때 query에 `for update`라고 명시적으로 업데이트를 위해 조회한다.
- Optimistic 과 달리 version같은게 없고 개발자는 lock을 신경 쓸 필요가 없지만 상황에 따라 DB에 부하가 있다. 

#### Optimistic Lock
- Optimistic Lock은 경합이 발생하지 않을 것을 '낙관적'으로 가정하고 실제 Lock이 아닌 데이터에 version을 부여해 관리한다.
- 두 스레드가 version이 동일한 데이터를 조회해 따로 업데이트할 때 먼저 업데이트한 쪽이 version까지 업데이트하기 때문에 뒤에 업데이트하는 쪽은 version이 달라 실패한다.
- 이 경우를 개발자가 예외로 처리해 줘야 하는 불편함이 있지만 실제 lock을 만드는 것이 아니기 때문에 DB에 부하는 없다.

#### Named Lock

### Redis의 Lock을 이용

#### Lettuce

#### Redisson