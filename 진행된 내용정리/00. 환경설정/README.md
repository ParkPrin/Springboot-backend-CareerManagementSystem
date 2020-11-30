# 00 환경설정

## 진행순서
0.진행된 내용정리 폴더 생성: 각 단계 별 진행된 내용 README.md로 정리
1. build.gradle 작성
2. Application.java 추가

## 각 단계 별 진행된 내용 README.md로 정리
진행된 내용을 각 단계별로 정리하는 글

## build.gradle 작성
의존성을 관리하는 파일 - 의존성에 대해서만 설명추

```
dependencies {가
    // Spring boot에서 REST 통신을 하기 위한 의존성
    implementation 'org.springframework.boot:spring-boot-starter-web'

    // Lombok 처리에 관한 의존성
    compileOnly 'org.projectlombok:lombok:1.18.8'
    annotationProcessor 'org.projectlombok:lombok:1.18.8'

    // 영속적 데이터 처리를 위한 의존성: JPA, JDBC, MariaDB
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    compile('org.springframework.session:spring-session-jdbc')
    compile("org.mariadb.jdbc:mariadb-java-client")

    // 보안처리에 대한 의존성: JWT, OAuth2 
    implementation 'org.springframework.boot:spring-boot-starter-security'
    compile('org.springframework.boot:spring-boot-starter-oauth2-client')
    compile group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'

    // 코드 테스트를 하기위한 의존성
    testCompile('org.springframework.boot:spring-boot-starter-test')
    testCompile('org.springframework.security:spring-security-test')

    // 개발시 사용하는 공통 Util에 대한 의존성
    compile group: 'org.apache.commons', name: 'commons-lang3', version: '3.11'

    // jdk 11 버전에서 security 의존성 사용시 오류해결에 관한 의존성
    implementation "jakarta.xml.bind:jakarta.xml.bind-api:2.3.2"
    implementation "org.glassfish.jaxb:jaxb-runtime:2.3.2"
}
```