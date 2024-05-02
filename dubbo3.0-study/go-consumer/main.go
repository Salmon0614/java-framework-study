package main

import (
	"context"
	"dubbo.apache.org/dubbo-go/v3/common/logger"
	"dubbo.apache.org/dubbo-go/v3/config"
	_ "dubbo.apache.org/dubbo-go/v3/imports"
	"go-consumer/api"
)

var userServiceImpl = new(api.UserServiceClientImpl)

// export DUBBO_GO_CONFIG_PATH=conf/dubbogo.yml
func main() {
	config.SetConsumerService(userServiceImpl)
	config.Load()
	logger.Info("start to test dubbo")
	req := &api.UserRequest{
		Uid: "1",
	}
	user, err := userServiceImpl.GetUser(context.Background(), req)
	if err != nil {
		logger.Error(err)
	}
	logger.Infof("client response result: %v\n", user)
}
