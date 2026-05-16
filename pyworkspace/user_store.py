"""
用户持久化存储模块
学号: 3123004163
姓名: 张逸壕
班级: 软件工程1班
"""

import json
import os
from typing import Protocol


class UserStore(Protocol):
  def load_all(self) -> dict[str, str]: ...
  def save_user(self, user_id: str, password: str) -> None: ...


class FileUserStore:
  def __init__(self, path: str):
    self.path = path
    directory = os.path.dirname(path)
    if directory:
      os.makedirs(directory, exist_ok=True)

  def load_all(self) -> dict[str, str]:
    if not os.path.exists(self.path):
      return {}
    try:
      with open(self.path, "r", encoding="utf-8") as file:
        data = json.load(file)
      if isinstance(data, dict):
        return {str(key): str(value) for key, value in data.items()}
    except (json.JSONDecodeError, OSError):
      return {}
    return {}

  def save_user(self, user_id: str, password: str) -> None:
    users = self.load_all()
    users[user_id] = password
    with open(self.path, "w", encoding="utf-8") as file:
      json.dump(users, file, ensure_ascii=False, indent=2)
