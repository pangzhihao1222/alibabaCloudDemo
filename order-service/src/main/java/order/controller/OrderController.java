package order.controller;

import net.xdclass.domain.Video;
import net.xdclass.domain.VideoOrder;
import order.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RefreshScope   //关键，能够动态刷新配置文件的内容，无需重启项目
@RestController
@RequestMapping("/api/v1/video_order/")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;  //可实现负载均衡

    @Autowired
    private VideoService videoService;

    @Value("${video.title}")
    private String videoTitle;

    @RequestMapping("find_by_id")
    public Object find_by_id(int videoId) {
        /*
            Video video = restTemplate.getForObject("http://localhost:9000/api/v1/video/find_by_id?videoId="+videoId, Video.class);
            1.ip写死
            2.不容易维护
            3.没有负载均衡
            结论:所以要进行服务治理
         */
        //List<ServiceInstance> list = discoveryClient.getInstances("video-service"); //拿到video-service当前服务的集群
        //ServiceInstance serviceInstance = list.get(0); //拿到第一个服务
        //Video video = restTemplate.getForObject("http://video-service/api/v1/video/find_by_id?videoId="+videoId, Video.class);

        Video video = videoService.findById(videoId);    //使用open-feign方法
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setVideoId(video.getId());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());
        videoOrder.setServerInfo(video.getServeInfo());
        return videoOrder;
    }

    /**
     * 测试feign 调用 使用post方式传输对象
     *
     * @param video
     */
    @RequestMapping("save")
    public int save(@RequestBody Video video) {
        temp++;
        if (temp % 3 == 0) {
            throw new RuntimeException();
        }
        int row = videoService.save(video);
        System.out.println(row);
        return row;
    }

    int temp = 0;

    @RequestMapping("list")
    public Object list(HttpServletRequest request) {
//        try {
//            TimeUnit.SECONDS.sleep(3); //睡眠3秒钟
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        temp++;
        if (temp % 3 == 0) {
            //throw new RuntimeException();
        }
        Map<String, String> map = new HashMap<>();
        map.put("title1", "AlibabaCloud微服务专题");
        map.put("title2", "AlibabaCloud面试专题");
        map.put("port", request.getServerPort() + "");
        map.put("refreshtest", videoTitle);
        return map;

    }
}
