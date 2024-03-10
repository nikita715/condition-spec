[![](https://jitpack.io/v/nikita715/condition-spec.svg)](https://jitpack.io/#nikita715/condition-spec)
[![pages-build-deployment](https://github.com/nikita715/condition-spec/actions/workflows/pages/pages-build-deployment/badge.svg?branch=master)](https://github.com/nikita715/condition-spec/actions/workflows/pages/pages-build-deployment)

# condition-spec
Collection of value classes that can be used for search in abstract data source. See the list of classes in [Dokka](https://nikita715.github.io/condition-spec)


### Usage


```
dependencies {
    implementation("com.github.nikita715.condition-spec:condition-spec-base:1.0.5")
}
```

Code example

```
interface UserService {

    fun findOneBy(
        id: SingleCondition<Long>? = null,
        firstName: Condition<String>? = null,
        lastName: Condition<String>? = null,
        birthDate: RangeCondition<LocalDateTime>? = null,
    ): UserModel?

}
```

```
userService.findOneBy(
    firstName = Eq("Bill"),
    lastName = NotIn("Stivenson", "Jackson") and Like("son"),
    birthDate = LessEq(LocalDateTime.now().minusYears(21)),
)
```

## Exposed adapter

Adapter that translates condition-spec conditions to exposed search conditions

### Usage

```
dependencies {
    implementation("com.github.nikita715.condition-spec:condition-spec-base:1.0.5")
    implementation("com.github.nikita715.condition-spec:condition-spec-exposed-adapter:1.0.5")
}
```

Code example

```
class UserServiceImpl: UserService {
    override fun findOneBy(
        id: SingleCondition<Long>?,
        firstName: Condition<String>?,
        lastName: Condition<String>?,
        birthDate: RangeCondition<LocalDateTime>?,
    ): UserModel? =
        UserTable.select {
            listOfNotNull(
                UserTable.id matches id,
                UserTable.firstName matches firstName,
                UserTable.lastName matches lastName,
                UserTable.birthDate matches birthDate,
            ).compoundAnd()
        }.limit(1).firstOrNull()?.toUserModel()

    ...
}
```
