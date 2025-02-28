begin
    for i IN 1..20 loop
    insert into member
    values(
    99999-i, --번호
    'friend'||i, --아이디
    'friend'||i, --비번
    case when mod(i,2) = 0 then 'M' else 'F' end, --성별
    01000000000, --폰
    19991010, --생일
    'email@email.com',
    'friend'||i, --닉네임
    default, --상태
    default, --관리자
    default); --생성일
end loop;
end;
/
commit;
