package dev.cheerfun.pixivic.biz.web.illust.controller;

import dev.cheerfun.pixivic.basic.auth.annotation.PermissionRequired;
import dev.cheerfun.pixivic.basic.ratelimit.annotation.RateLimit;
import dev.cheerfun.pixivic.biz.ad.annotation.WithAdvertisement;
import dev.cheerfun.pixivic.biz.userInfo.annotation.WithUserInfo;
import dev.cheerfun.pixivic.biz.web.illust.service.IllustrationBizService;
import dev.cheerfun.pixivic.biz.web.illust.service.SearchService;
import dev.cheerfun.pixivic.common.po.Illustration;
import dev.cheerfun.pixivic.common.po.Result;
import dev.cheerfun.pixivic.common.po.illust.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author OysterQAQ
 * @version 1.0
 * @date 2019/09/28 16:10
 * @description IllustrationController
 */
@RestController
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IllustrationBizController {
    private final IllustrationBizService illustrationBizService;
    private final SearchService searchService;

    @GetMapping("/tags/{tag}/translation")
    public ResponseEntity<Result<Tag>> translationTag(@PathVariable String tag) {
        return ResponseEntity.ok().body(new Result<>("获取标签翻译成功", illustrationBizService.translationTag(tag)));
    }

    @GetMapping("/exists/illust/{id}")
    //@PermissionRequired
    public ResponseEntity<Result<Boolean>> queryExistsById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(new Result<>("获取存在详情成功", illustrationBizService.queryExistsById(id)));
    }

    @GetMapping("/illusts/{illustId}")
    @RateLimit
    @PermissionRequired
    public ResponseEntity<Result<Illustration>> queryIllustrationById(@PathVariable Integer illustId, @RequestHeader(value = "Authorization", required = false) String token) {
        return ResponseEntity.ok().body(new Result<>("获取画作详情成功", illustrationBizService.queryIllustrationByIdWithUserInfo(illustId)));
    }

    @GetMapping("/illusts/{illustId}/related")
    @WithUserInfo
    @WithAdvertisement
    @RateLimit
    public CompletableFuture<ResponseEntity<Result<List<Illustration>>>> queryIllustrationRelated(@PathVariable Integer illustId, @RequestParam(defaultValue = "1") @Max(333) int page, @RequestParam(defaultValue = "30") int pageSize, @RequestHeader(value = "Authorization") String token) {
        return searchService.queryIllustrationRelated(illustId, page, pageSize).thenApply(r -> ResponseEntity.ok().body(new Result<>("获取关联画作成功", illustrationBizService.queryIllustrationByIllustIdList(r))));
    }

    @GetMapping("/illusts/random")
    public ResponseEntity<Result<Illustration>> queryRandomIllustration(
            @RequestParam(defaultValue = "original") String urlType,
            @RequestParam(defaultValue = "illust") String illustType,
            @RequestParam(defaultValue = "false") Boolean detail,
            @RequestParam(defaultValue = "1") Float range,
            @RequestParam(defaultValue = "16:9") String ratio,
            @RequestParam(defaultValue = "4") Integer maxSanityLevel
    ) {
        String url = illustrationBizService.queryRandomIllustration(urlType, illustType, detail, ratio, range, maxSanityLevel);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", url).header("Cache-Control", "no-cache").body(null);
    }

    @GetMapping("/tags/{tag}/candidates")
    public ResponseEntity<Result<List<Tag>>> autoCompleteTag(@PathVariable String tag, @RequestBody List<String> tagList) {
        return ResponseEntity.ok().body(new Result<>("获取标签候选成功", null));
    }

}
