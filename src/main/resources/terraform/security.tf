resource "aws_security_group" "dockert2s_security_group" {
  name        = "dockert2s_security_group"
  description = "Grupo de seguranca"
  vpc_id      = aws_vpc.dockert2s_vpc_1.id

}

resource "aws_security_group_rule" "dockert2s_rules_saida" {
  from_port         = 0
  protocol          = "-1"
  security_group_id = aws_security_group.dockert2s_security_group.id
  to_port           = 0
  type              = "egress"
  cidr_blocks       = ["0.0.0.0/0"]
}

resource "aws_security_group_rule" "dockert2s_rules_http" {
  from_port         = 80
  protocol          = "tcp"
  security_group_id = aws_security_group.dockert2s_security_group.id
  to_port           = 80
  type              = "ingress"
  cidr_blocks       = ["0.0.0.0/0"]
}

resource "aws_security_group_rule" "dockert2s_rules_entrada" {
  from_port         = 22
  protocol          = "tcp"
  security_group_id = aws_security_group.dockert2s_security_group.id
  to_port           = 22
  type              = "ingress"
  cidr_blocks       = ["0.0.0.0/0"]
}

resource "aws_key_pair" "dockert2s_keypair" {
  key_name   = "dockert2s_key"
  public_key = file("~/.ssh/dockert2s_key.pub")
}