# GetCommand
通过已有的物品、方块、实体获取对应的`give`、`setblock`、`summon`指令

# 环境要求
* `Java 8`以上
* `CraftBukkit`服务端，`1.8.3` ~ `1.13.2`

# 示例图片
![](https://camo.githubusercontent.com/a491d76d9f32026f1bbd9ed56b34570007abaa30/687474703a2f2f7777332e73696e61696d672e636e2f6c617267652f39313033613532306a7731663274367869766168716a32306b793064363130392e6a7067)

# 快速入门
1. 下载本插件并放进服务器的`plugins`文件夹里
2. 启动服务器
3. 由玩家执行`/getcommand`来查看帮助
4. 执行`/getcommand block`
5. 随便找个方块左键点一下
6. 执行`/getcommand save`来查看获取到的命令
7. 执行`/getcommand save command_block`
8. 在创造模式下左键点一下一个没有命令的命令方块
9. 刚刚的命令就保存到这个命令方块里了

# 命令
1. /getcommand help [子命令名]
    * 查看帮助，省略参数则显示子命令列表
    * 只显示有权限执行的子命令
    * 简写 h
2. /getcommand item
    * 通过手上拿着的物品来获取 give 命令
    * 简写 i
3. /getcommand entity
    * 执行后左键点一下实体可以获取这个实体的 summon 命令
    * 简写 e
4. /getcommand block
    * 执行后左键点一下方块可以获取这个方块的 setblock 命令
    * 简写 b
5. /getcommand save [chat | file | console | command_block]
    * 将已获取到的命令保存，可选输出到聊天窗口、服务器控制台、以及写入命令方块
    * chat: 输出到聊天窗口(无参数时默认用这个)
    * file: 输出到文件(本功能暂未实现)
        * 简写 f
    * console: 输出到服务器控制台
        * 简写 c
    * command_block: 执行后在创造模式下左键点一下一个没有命令的命令方块可以保存到命令方块中
        * 简写 cb
6. /getcommand cancel
    * 取消待执行的操作，如执行 entity 后等待你左键点一下实体的操作
    * 简写 c

<!---->
* [xxx] 这种表示可选参数
* 除了 help 以外的命令都只能由玩家执行

# 权限
1. getcommand.use: 允许使用 getcommand 命令以及 help 子命令
2. getcommand.item: 允许使用 item 子命令
3. getcommand.block: 允许使用 block 子命令
4. getcommand.entity: 允许使用 entity 子命令
5. getcommand.save: 允许使用 save 子命令
6. getcommand.save.chat: 允许 save 在聊天窗口输出
7. getcommand.save.file: 允许 save 保存到文件
8. getcommand.save.console: 允许 save 输出到服务器控制台
9. getcommand.save.command_block: 允许 save 保存到命令方块
10. getcommand.cancel: 允许使用 cancel 子命令

<!---->
* 每个命令都有对应的权限，所有权限默认均为 OP 可用。

# 开发者
<!-- TODO 环境准备 -->
