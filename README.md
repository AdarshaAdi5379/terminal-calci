# 🧮 Terminal Calci (Java + Docker + GitHub Actions)

A lightweight **Java-based command-line calculator** containerized with **Docker** and automatically deployed to **Docker Hub** using **GitHub Actions CI/CD**. Designed to demonstrate full-stack **DevOps** and **Cloud-Native** automation.

---

## 🚀 Quick Start

### Run interactive calculator
```bash
docker run --rm -it adarsha5389/terminal-calci:latest

## Run single expression
```bash
 docker run --rm adarsha5389/terminal-calci:latest "5+5"

//
⚙️ Tech Stack

Java (Temurin JDK 25) · Docker · GitHub Actions · Docker Hub · CI/CD · DevOps Automation

📦 Features

Modular Java CLI calculator (Main.java, Calculator.java)

Multi-stage Dockerfile for small, efficient images (JDK → JRE)

Automated build & push pipeline with GitHub Actions

Secure deployment using GitHub Secrets:

DOCKERHUB_USER = adarsha5389

DOCKERHUB_TOKEN = Docker Hub Access Token

Auto-versioning: pushes tags like v1.1 and updates latest

Works on all OS platforms via Docker

Demonstrates end-to-end CI/CD workflow

//
#trigger a build

git tag v1.1
git push origin v1.1

##Example usage
 > 2+3*4
14.0
> x=7
7.0
> x^2
49.0
> exit
Goodbye.


🐳 Docker Hub

📦 Image: [adarsha5389/terminal-calci](https://hub.docker.com/repository/docker/adarsha5389/terminal-calci/general)

📥 Pull: docker pull adarsha5389/terminal-calci:latest
