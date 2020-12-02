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

## 개발환경 DB 구축 - Docker Mariadb
```
docker run -d \
-p 2012:3306 \
--name CareerManagementPlatform-db \
-e MYSQL_ROOT_PASSWORD=루트 비밀번호 \
-e MYSQL_DATABASE=career_management_platform \
-e MYSQL_USER=cmp_admin \
-e MYSQL_PASSWORD=cmp_admin의 비밀번호 \
mariadb:latest
```

데이터베이스 IDE을 이용하여 root 계정으로 접속한 후 데이터베이스의 환경을 변경한다
1) 타임존 설정
```
$ SET GLOBAL time_zone ='Asia/Seoul';
$ SET time_zone ='Asia/Seoul';
$ select @@global.time_zone, @@session.time_zone;
```

2) Character Set UTF-8설정


```
root 권한으로 들어가서 아래 명령어를 클릭한다
$ show variables like 'c%'
```

그럼 매칭되어 있는 값중에 latin으로 되어있는 값을 utf8로 변환하여야 한다.
그런데 Docker image에서는 utf8로 설정을 해놓아서 딱히 작업할 내용이 없었다.

3) schema-mysql.sql 실행
아래 쿼리를 실행하여 오류를 방지한다.
```
CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

```